// write the IceCreamOrder class here

class IceCreamOrder {
    var price: Int = 0

    constructor(popsicles: Int) {
        price = popsicles * 7
    }

    constructor(scoops: Int, toppings: Int) {
        price = scoops * 5 + toppings * 2
    }
}