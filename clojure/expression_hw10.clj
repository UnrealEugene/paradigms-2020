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

(def ops
  {"+" add, "-" subtract,
   "*" multiply, "/" divide,
   "negate" negate,
   "sumexp" sumexp, "softmax" softmax})

(defn parseFunction [input]
  (let [parse_ (fn rec [x]
                 (cond
                   (list? x) (apply (get ops (str (first x))) (mapv rec (rest x)))
                   (number? x) (constant x)
                   :else (variable (str x))))]
    (parse_ (read-string input))))