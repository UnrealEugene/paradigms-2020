"use strict";

let variables = ['x', 'y', 'z'];  // Переменные и их порядок определяется здесь


function Const(value) {
    this.value = value;
}
Const.prototype.evaluate = function() {
    return this.value;
};
Const.prototype.toString = function() {
    return this.value.toString();
};
Const.prototype.diff = function() {
    return new Const(0);
};


function Variable(name) {
    this.name = name;
}
Variable.prototype.evaluate = function(...argVar) {
    return argVar[variables.indexOf(this.name)];
};
Variable.prototype.toString = function() {
    return this.name;
};
Variable.prototype.diff = function(v) {
    return new Const(this.name === v ? 1 : 0);
};


function Operation(sign, op, diffOp, ...args) {
    this.sign = sign;
    this.op = op;
    this.diffOp = diffOp;
    this.args = args;
}
Operation.prototype.evaluate = function(...argsVar) {
    return this.op(...this.args.map(a => a.evaluate(...argsVar)));
};
Operation.prototype.toString = function() {
    return this.args.join(" ") + " " + this.sign;
};
Operation.prototype.diff = function(v) {
    return this.diffOp(...this.args, ...this.args.map(a => a.diff(v)));
};


function Custom(sign, op, diffOp) {
    let constructor = function(...argsOp) {
        Operation.call(this, sign, op, diffOp, ...argsOp);
    };
    constructor.prototype = Object.create(Operation.prototype);
    return constructor;
}

let Add = Custom(
    "+", 
    (x, y) => x + y,
    (u, v, du, dv) => new Add(du, dv)  // du + dv
);
let Subtract = Custom(
    "-", 
    (x, y) => x - y, 
    (u, v, du, dv) => new Subtract(du, dv)  // du - dv
);
let Multiply = Custom(
    "*", 
    (x, y) => x * y, 
    (u, v, du, dv) => new Add(
        new Multiply(du, v),
        new Multiply(dv, u)
    )  // du * v + dv * u
);
let Divide = Custom(
    "/",
    (x, y) => x / y, 
    (u, v, du, dv) => new Divide(
        new Subtract(
            new Multiply(du, v),
            new Multiply(dv, u)
        ),
        new Multiply(v, v)
    )  // (du * v - dv * u) / v^2
);
let Negate = Custom(
    "negate", 
    x => -x, 
    (u, du) => new Negate(du)  // -du
);

function Op(args, func) {  // Operation constructor
    this.args = args;
    this.func = func;
}

let ops = {  // Operations and special constants
    "+": new Op(2, Add),
    "-": new Op(2, Subtract),
    "*": new Op(2, Multiply),
    "/": new Op(2, Divide),
    "negate": new Op(1, Negate),
};

function parse(input) {
    let tokens = input.split(" ").filter(x => x.length > 0);  // Array of operands
    let stack = [];                                           // Stack of processed operands
    for (let i = 0; i < tokens.length; i++) {
        let token = tokens[i];
        if (token in ops) {                          // Operations and special constants
            let op = ops[token];
            let argsCnt = op.args;
            let args = [];
            for (let i = 0; i < argsCnt; i++) {
                args.push(stack.pop());
            }
            stack.push(new op.func(...args.reverse()));
        } else if (variables.includes(token)) {      // Variables (условие оптимизируется компилятором)
            stack.push(new Variable(token));
        } else {                                     // Constants
            stack.push(new Const(+token));
        }
    }
    return stack[0];
}