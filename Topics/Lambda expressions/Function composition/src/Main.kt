fun compose(g: (Int) -> Int, h: (Int) -> Int): (Int) -> Int {
    return { c -> g(h(c)) }
}