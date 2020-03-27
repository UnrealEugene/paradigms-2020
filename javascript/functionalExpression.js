"use strict";

let custom = (op) => (...argsOp) => (...argsVar) => op(...argsOp.map(a => a(...argsVar)));

let variables = new Map([["x", 0], ["y", 1], ["z", 2]]);  // Переменные

let variable = (v) => (...args) => args[variables.get(v)];


let cnst = v => () => v;
let one = cnst(1);
let two = cnst(2);
let add = custom((x, y) => x + y);
let subtract = custom((x, y) => x - y);
let multiply = custom((x, y) => x * y);
let divide = custom((x, y) => x / y);
let negate = custom(x => -x);
let abs = custom(Math.abs);
let iff = custom((x, y, z) => x >= 0 ? y : z);

function Op(args, func) {  // Operation constructor
    this.args = args;
    this.func = func;
}

let ops = {  // Operations and special constants
    "iff": new Op(3, iff),
    "+": new Op(2, add),
    "-": new Op(2, subtract),
    "*": new Op(2, multiply),
    "/": new Op(2, divide),
    "negate": new Op(1, negate),
    "abs": new Op(1, abs),
    "one": new Op(0, () => one),
    "two": new Op(0, () => two)
};

function parse(input) {
    return input.split(" ").filter(x => x.length > 0).reduce((stack, token) => {
        if (token in ops) {                          // Operations and special constants
            let op = ops[token];
            stack.push(op.func(...stack.splice(-op.args)));
        } else if (variables.has(token)) {      // Variables
            stack.push(variable(token));
        } else {                                     // Constants
            stack.push(cnst(+token));
        }
        return stack;
    }, [])[0];
}

let e = add(
    subtract(
        multiply(
            variable('x'),
            variable('x')
        ),
        multiply(
            cnst(2),
            variable('x')
        ),
    ),
    cnst(1)
);
for (let x = 0; x < 10; x++) {
    console.log(e(x));
}