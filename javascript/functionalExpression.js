"use strict";

let custom = (op) =>
    (...argsOp) =>
        (...argsVar) =>
            op.apply(null, argsOp.map(a => a.apply(null, argsVar)));

let variables = ["x", "y", "z"];

function variable(v) {
    return function(...args) {
        return args[variables.indexOf(v)];
    };
}
let cnst = v => () => v;
let e = cnst(Math.E);
let pi = cnst(Math.PI);
let add = custom((x, y) => x + y);
let subtract = custom((x, y) => x - y);
let multiply = custom((x, y) => x * y);
let divide = custom((x, y) => x / y);
let negate = custom(x => -x);
let sin = custom(Math.sin);
let cos = custom(Math.cos);
let cube = custom(x => Math.pow(x, 3.0));
let cuberoot = custom(Math.cbrt);

let binaryOps = {
    "+": add,
    "-": subtract,
    "*": multiply,
    "/": divide
};
let unaryOps = {
    "negate": negate,
    "sin": sin,
    "cos": cos,
    "cube": cube,
    "cuberoot": cuberoot
};
let consts = {
    "e": e,
    "pi": pi
};

function parse(input) {
    let args = input.split(" ").filter(x => x.length > 0);  // Array of operands
    let stack = [];                                         // Stack of processed operands
    for (let i = 0; i < args.length; i++) {
        if (binaryOps[args[i]] !== undefined) {             // Binary operations
            let right = stack.pop();
            let left = stack.pop();
            stack.push(binaryOps[args[i]](left, right));
        } else if (unaryOps[args[i]] !== undefined) {       // Unary operations
            stack.push(unaryOps[args[i]](stack.pop()));
        } else if (variables.includes(args[i])) {           // Variables
            stack.push(variable(args[i]));
        } else if (consts[args[i]] !== undefined) {         // Special constants
            stack.push(consts[args[i]]);
        } else {
            stack.push(cnst(+args[i]));                     // Constants
        }
    }
    return stack[0];
}

console.log(parse("x 2 +")(0, 0, 0));
console.log(add(variable('x'), cnst(2))(0, 0, 0));