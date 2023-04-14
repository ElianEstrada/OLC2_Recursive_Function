int suma (a, b) {
    return a + b
}

int factorial (num) {
    if (num == 1) {
        return 1
    }

    return num * factorial(num - 1)
}

void main() {
    print("hola")
    print("Adios")
    print(suma(2,3))
    print(factorial(5))
}