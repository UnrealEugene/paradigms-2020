(ns test)

(deftype ConstantT [val])
(derive ConstantT ::expression)

(deftype VariableT [var])
(derive VariableT ::expression)

(derive ::operation ::expression)

(deftype AddT [& args])
(derive AddT ::operation)

(deftype SubtractT [& args])
(derive SubtractT ::operation)

(defmulti op class)
(defmethod op AddT [_] +)
(defmethod op SubtractT [_] -)

(defmulti evaluate (fn [this mp] (class this)))
(defmethod evaluate ::operation )

(println (toString (new Add (new Constant 2) (new Constant 3))))




