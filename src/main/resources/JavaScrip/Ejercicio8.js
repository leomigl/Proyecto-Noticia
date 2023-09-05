var numeros = [];
do {
    var num = parseInt(prompt("ingrese un numero"));
    if (isNaN(num)) {
        alert(`error, ingrese un numero`);
    }
    else if (num !== 0) {
        numeros.push(num);
    }
    else if (num === 0) {
        alert(`el maximo valor es ${Math.max(...numeros)}`)
        alert(`el minimo valor es ${Math.min(...numeros)}`)
        var acumulado = numeros.reduce((a, b) => a + b, 0)
        alert(`el promedio es ${acumulado/numeros.length}`);
    }
} while (num !== 0);