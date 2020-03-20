"use strict";

let cnst = value => () => value;
const variable = () => x => x;
const parse = input => input.trim() === "x" ? variable("x") : cnst(+input);
