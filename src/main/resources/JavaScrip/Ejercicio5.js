
var num1=parseInt(prompt("Ingrese un numero"));
var num2=parseInt(prompt("Ingrese otro numero"));
var opc=prompt("Ingrese una opcion:\n 1-suma(S o s)\n 2-resta(R o r)\n 3-division(D o d)\n 4-multiplicacion(M o m)");
opc=opc.toLowerCase();
switch (opc) {
    case  's':
        var suma= num1+num2;
        alert(suma);
        break;
    case 'r' :
        var resta=num1-num2;
        alert(resta);
        break;
    case 'd' :
        var division=num1/num2;
        alert(division);
        break;
    case 'm' :
        var multiplicacion=num1*num2;         
        alert(multiplicacion);
        break;
    default:
        alert("error");
        break;
}