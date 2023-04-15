int fibo (num) {
    if (num < 2) {
        return num
    }

    return fibo(num - 1) + fibo(num - 2)
}

void main(){
    print(fibo(3))
}