(defn expression [op]
  (fn [& args]
    (fn [vars]
      (apply op (mapv #(%1 vars) args)))))

(def add (expression +))
(def subtract (expression -))
(def multiply (expression *))
(def divide (expression /))

(defn constant [x] (fn [vars] x))
(defn variable [var] (fn [vars] (get vars var)))

(defn parseFunction [input]
  (let []
    ()))

(def expr
  (subtract
    (multiply
      (constant 2)
      (variable "x"))
    (constant 3)))

(println (expr {"x" 2}))