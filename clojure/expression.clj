; +----+
; |HW10|
; +----+

(defn expression [op] (fn [& args] (fn [vars] (apply op (mapv #(%1 vars) args)))))

(def add (expression +))
(def subtract (expression -))
(def multiply (expression *))
(def divide (expression #(/ (double %1) (apply * %&))))
(def negate (expression -))

(defn f-sumexp [& args] (apply + (mapv #(Math/exp %) args)))
(def sumexp (expression f-sumexp))
(defn f-softmax [& args] (/ (Math/exp (first args)) (apply f-sumexp args)))
(def softmax (expression f-softmax))

(defn constant [x] (fn [vars] x))
(defn variable [var] (fn [vars] (get vars var)))

(def ops-function
  {"+" add, "-" subtract,
   "*" multiply, "/" divide,
   "negate" negate,
   "sumexp" sumexp, "softmax" softmax})

(defn create-parser [var-cons const-cons ops-map]
  (fn [input]
    ((fn rec [x]
       (cond
         (list? x) (apply (get ops-map (str (first x))) (mapv rec (rest x)))
         (number? x) (const-cons x)
         :else (var-cons (str x)))) (read-string input))))

(def parseFunction (create-parser variable constant ops-function))

; +----+
; |HW11|
; +----+

(defn proto-get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :proto) (recur (obj :proto) key)))

(defn proto-call [obj key & args]
  (apply (proto-get obj key) obj args))
(defn method [key]
  (fn [obj & args] (apply proto-call obj key args)))
(defn constructor [cons proto]
  (fn [& args] (apply cons {:proto proto} args)))

(def diff (method :diff))
(def evaluate (method :eval))
(def toString (method :to-str))
(def toStringInfix (method :to-str-infix))                  ; for HW12

(declare zero)

(def constant-proto
  {:eval (fn [this vars] (proto-get this :value)),
   :diff (fn [this var] zero),
   :to-str (fn [this] (format "%.1f" (proto-get this :value))),
   :to-str-infix (method :to-str)                           ; for HW12
   })
(defn constant-cons [this value]
  (assoc this :value value))
(def Constant (constructor constant-cons constant-proto))

(def zero (Constant 0.0))
(def one (Constant 1.0))

(def variable-proto
  {:eval (fn [this vars] (vars (proto-get this :name))),
   :diff (fn [this var] (if (= var (proto-get this :name)) one zero)),
   :to-str (fn [this] (proto-get this :name)),
   :to-str-infix (method :to-str)                           ; for HW12
   })
(defn variable-cons [this name]
  (assoc this :name name))
(def Variable (constructor variable-cons variable-proto))

(def operation-proto
  {:eval (fn [this vars]
           (apply (proto-get this :op) (mapv #(proto-call % :eval vars) (proto-get this :args)))),
   :diff (fn [this var]
           ((proto-get this :diff-op)
             (proto-get this :args)
             (mapv #(proto-call % :diff var) (proto-get this :args)))),
   :to-str (fn rec [this]
             (str "(" (proto-get this :sign) " "
                  (clojure.string/join " " (mapv rec (proto-get this :args))) ")")),
   :to-str-infix (fn rec [this]
                   (if (proto-get this :infix)
                     (str "("
                          (clojure.string/join
                            (str " " (proto-get this :sign) " ")
                            (mapv (method :to-str-infix) (proto-get this :args)))
                          ")")
                     (str "(" (proto-get this :sign) " "
                          (clojure.string/join " " (mapv (method :to-str-infix) (proto-get this :args))) ")")))
   })
(defn operation-cons [this & args]
  (assoc this :args args))

(defn custom-op [sign op diff-op infix]
  (let [custom-proto {:proto operation-proto, :sign sign, :op op, :diff-op diff-op, :infix infix}]
    (constructor operation-cons custom-proto)))

(def Add
  (custom-op
    "+"
    +
    (fn [v dv] (apply Add dv))
    true))

(def Subtract
  (custom-op
    "-"
    -
    (fn [v dv] (apply Subtract dv))
    true))

(def Negate
  (custom-op
    "negate"
    -
    (fn [v dv] (apply Negate dv))
    false))

(declare Multiply)
(defn multiply-diff [v dv]
  (fnext (reduce
           (fn [acc next]
             (list
               (Multiply (first acc) (first next))
               (Add
                 (Multiply (fnext acc) (first next))
                 (Multiply (first acc) (fnext next)))))
           (list (first v) (first dv))
           (mapv list (rest v) (rest dv)))))
(def Multiply
  (custom-op
    "*"
    *
    multiply-diff
    true))

(declare Divide)
(defn divide-diff [v dv]
  (if (= 1 (count v))
    (Divide
      (Negate (first dv))
      (Multiply (first v) (first v)))
    (Divide
      (Subtract
        (apply Multiply (first dv) (rest v))
        (Multiply (first v) (multiply-diff (rest v) (rest dv))))
      (letfn [(Sqr [x] (Multiply x x))]
        (Sqr (apply Multiply (rest v)))))))
(def Divide
  (custom-op
    "/"
    #(if (empty? %&) (/ 1.0 %1) (/ (double %1) (apply * %&)))
    divide-diff
    true))

(declare Sumexp)
(defn sumexp-diff [v dv] (apply Add (mapv #(Multiply (Sumexp %1) %2) v dv)))
(def Sumexp
  (custom-op
    "sumexp"
    f-sumexp
    sumexp-diff
    false))

(def Softmax
  (custom-op
    "softmax"
    f-softmax
    (fn [v dv]
      (divide-diff
        (list
          (Sumexp (first v))
          (apply Sumexp v))
        (list
          (sumexp-diff (list (first v)) (list (first dv)))
          (sumexp-diff v dv))))
    false))

(def ops-object
  {"+" Add, "-" Subtract,
   "*" Multiply, "/" Divide,
   "negate" Negate,
   "sumexp" Sumexp, "softmax" Softmax})

(def parseObject (create-parser Variable Constant ops-object))

; +----+
; |HW12|
; +----+

(defn -return [value tail] {:value value, :tail tail})
(def -value :value)
(def -tail :tail)
(def -valid boolean)

(defn _show [result]
  (if (-valid result)
    (str " -> " (pr-str (-value result)) " | " (pr-str (apply str (-tail result))))
    "!"))

(defn _tabulate [parser inputs]
  (run! (fn [input] (printf "   %-10s %s\n" (pr-str input) (_show (parser input)))) inputs))

(defn _show-obj [result]
  (if (-valid result)
    (str " -> " (toStringInfix (-value result)) " | " (pr-str (apply str (-tail result))))
    "!"))

(defn _tabulate-obj [parser inputs]
  (run! (fn [input] (printf "   %-10s %s\n" (pr-str input) (_show-obj (parser input)))) inputs))

(defn _empty [value] (partial -return value))

(defn _char [pred]
  (fn [[c & cs]]
    (if (and c (pred c)) (-return c cs))))

(defn _map [f result]
  (if (-valid result)
    (-return (f (-value result)) (-tail result))))

;(_tabulate (comp (partial _map #(Character/toUpperCase %)) (_char #{\a \b \c})) ["a" "a~" "ba" "qa"])

(defn _combine [f a b]
  (fn [input]
    (let [ar ((force a) input)]
      (if (-valid ar)
        (_map (fn [bv] (f (-value ar) bv))
              ((force b) (-tail ar)))))))

(defn _either [a b]
  (fn [input]
    (let [ar ((force a) input)]
      (if (-valid ar)
        ar
        ((force b) input)))))

(defn _parser [p]                                           ; sth -> sth
  (fn [input] (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))
(defn +char [chars] (_char (set chars)))                    ; string -> symbol (in string)
(defn +char-not [chars] (_char (comp not (set chars))))     ; string -> symbol (not in string)
(defn +map [f parser] (comp (partial _map f) parser))       ; sth -> mapped f to sth
(def +ignore (partial +map (constantly 'ignore)))           ; sth -> 'ignore
(defn iconj [coll value]
  (if (= value 'ignore) coll (conj coll value)))
(defn +seq [& ps]                                           ; list of sth -> list of sth
  (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps]                                        ; list of sth -> mapped f to list of sth
  (+map (partial apply f) (apply +seq ps)))
(defn +or [p & ps] (reduce _either p ps))                   ; list of sth -> sth
(defn +opt [parser] (+or parser (_empty nil)))              ; sth -> sth or nil
(defn +star [parser]                                        ; sth -> list of sth
  (letfn [(rec [] (+or (+seqf cons parser (delay (rec))) (_empty ())))] (rec)))
(defn +plus [parser]                                        ; sth -> list of sth
  (+seqf cons parser (+star parser)))
(defn +seqn [n & ps]                                        ; list of sth -> sth
  (apply +seqf (fn [values] (nth values n)) ps))
(defn +nth [n p] (+map #(nth % n) p))                      ; list of sth -> sth
(defn +exact [[& cs]] (apply +seq (mapv #(+char (str %)) cs))) ; string -> string
(defn +str [parser] (+map (partial apply str) parser))      ; list of sth -> string via concat
(def *digit (+char "0123456789"))                           ; -> symbol (digit)
(def *number
  (+map (comp Constant double read-string)
        (+str (+seq
                (+opt (+char "+-"))
                (+str (+plus *digit))
                (+opt (+str (+seq (+char ".") (+str (+star *digit))))))))) ; -> string ("cnst(%)")
(def *letter (_char #(Character/isLetter %)))               ; -> symbol (letter)
(def *operand (+str (+seqf cons *letter (+star (+or *letter *digit))))) ; -> string (operand)
(def *space (_char #(Character/isWhitespace %)))            ; -> symbol (whitespace)
(def *not-space (_char (comp not #(Character/isWhitespace %)))) ; -> symbol (not whitespace)
(def *ws (+ignore (+star *space)))                          ; -> 'ignore (whitespace)

(def variables '("x" "y" "z"))
(def *variable (+map Variable (+str (apply +or (mapv #(+exact %) variables))))) ; -> string ("var(%)")

(declare *factor *term *expr *brackets)
(declare *term *prior-min *prior-max)
(def *brackets (+nth 0 (+seq (+ignore (+char "(")) *ws (delay *prior-min) *ws (+ignore (+char ")")))))
(def *term (+nth 0 (+seq *ws (+or *number *variable *brackets))))

(def infix-ops-by-priority '("+-" "*/" ))
(def unary-ops '("negate" "sumexp" "softmax"))

(def *prior-max
  (+map (fn [[f lst]] (reduce #((ops-object (str (first %2))) %1 (fnext %2)) f lst))
        (+nth 0 (+seq *ws (+seq *term (+star (+seq *ws (+char "*/") *term)))))))

(def *prior-min
  (+map (fn [[f lst]] (reduce #((ops-object (str (first %2))) %1 (fnext %2)) f lst))
        (+nth 0 (+seq *ws (+seq *prior-max (+star (+seq *ws (+char "+-") *prior-max)))))))

(_tabulate-obj *prior-min ["2"])
(_tabulate-obj *prior-min ["2" "2.5" "1." "( 2  )" "x" "x+2" "3+4-7" "2  + 2 * 2" "3 / 3 / 3" "x * (3 - y) + z / 3"])
(println (double (read-string "2.5")))

(def parseObjectInfix (_parser *prior-min))