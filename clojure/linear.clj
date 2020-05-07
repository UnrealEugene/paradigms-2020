(defn is-tensor? [x]
  (let [nullify (fn rec [y]
                  (cond
                    (vector? y) (mapv rec y)
                    (number? y) 0
                    :else nil))
        null-tensor? (fn rec [x] (or
                                   (and
                                     (number? x)
                                     (== x 0))
                                   (and
                                     (vector? x)
                                     (not (empty? x))
                                     (every? rec x)
                                     (apply = x))))]
    (null-tensor? (nullify x))))                            ;прямоугольный тензор чисел?

(defn shape [x]
  (let [shape_ (fn rec [x] (if (vector? x) (cons (count x) (rec (first x))) ()))]
    (shape_ x)))                                  ;форма тензора

(defn is-n-dim? [n x] (and (is-tensor? x) (= (count (shape x)) n)))
(def is-vector? (partial is-n-dim? 1))
(def is-matrix? (partial is-n-dim? 2))

(defn v_ [op]
  (fn [& args]
    {:pre [(every? is-tensor? args) (apply = (mapv shape args))]}
    (apply mapv op args)))

(def v+ (v_ +))
(def v- (v_ -))
(def v* (v_ *))
(def m+ (v_ v+))
(def m- (v_ v-))
(def m* (v_ v*))
(defn scalar [a b]
  {:pre [(is-vector? a) (is-vector? b)]}
  (reduce + (v* a b)))

(defn vect [& v]
  (reduce
    (fn [a b]
      {:pre [(is-vector? a) (= (shape a) (list 3)) (is-vector? b) (= (shape b) [3])]
       :post [(is-vector? %) (= (shape %) (list 3)) (= (scalar a %) 0) (= (scalar b %) 0)]}
      (let [det2v (fn [i j] (- (* (nth a i) (nth b j)) (* (nth a j) (nth b i))))]
        [(det2v 1 2) (det2v 2 0) (det2v 0 1)]))
    v))

(defn _*s [dim op]
  (fn [x & s]
    {:pre [(is-n-dim? dim x)]
     :post [(is-n-dim? dim %) (= (shape %) (shape x))]}
    ((fn [c] (mapv (fn [x_] (op x_ c)) x)) (apply * s))))

(def v*s (_*s 1 *))
(def m*s (_*s 2 v*s))

(defn transpose [m]
  {:pre [(is-matrix? m)]
   :post [(is-matrix? %) (= (shape m) (reverse (shape %)))]}
  (apply mapv vector m))

(defn m*v [m v]
  {:pre [(is-matrix? m) (is-vector? v) (= (nth (shape m) 1) (nth (shape v) 0))]
   :post [(is-vector? %) (= (nth (shape %) 0) (nth (shape m) 0))]}
  (mapv (fn [x] (scalar x v)) m))

(defn m*m [& m]
  (reduce
    (fn [a b]
      {:pre [(is-matrix? a) (is-matrix? b) (= (nth (shape a) 1) (nth (shape b) 0))]
       :post [(is-matrix? %) (= (nth (shape %) 0) (nth (shape a) 0)) (= (nth (shape %) 1) (nth (shape b) 1))]}
      (transpose (mapv (fn [x] (m*v a x)) (transpose b))))
    m))

(defn max-comp [cmp? & args] (reduce (fn [a b] (if (cmp? a b) b a)) args))

(defn broadcast [& args]
  (let [cons-from-shape (fn rec [d x] (if (empty? d) x (vec (take (first d) (repeat (rec (rest d) x))))))
        broadcast2 (fn [from to from-shape to-shape from-dim to-dim]
                     {:pre [(is-tensor? from)
                            (is-tensor? to)
                            (<= from-dim to-dim)
                            (= from-shape (drop (- to-dim from-dim) to-shape))]
                      :post [(is-tensor? %)
                             (= (shape %) to-shape)]}
                     (cons-from-shape (take (- to-dim from-dim) to-shape) from))
        max-elem (apply max-comp #(< (count (shape %1)) (count (shape %2))) args)
        max-elem-shape (shape max-elem)
        max-elem-dim (count max-elem-shape)]
    (mapv #(broadcast2 %1 max-elem (shape %1) max-elem-shape (count (shape %1)) max-elem-dim) args)))

(defn b_ [op]
  (let [b-eq_ (fn rec [& args] (apply (if (vector? (first args)) (v_ rec) op) args))]
    (fn [& args] (apply b-eq_ (apply broadcast args)))))

(def b+ (b_ +))
(def b- (b_ -))
(def b* (b_ *))
(def bd (b_ /))