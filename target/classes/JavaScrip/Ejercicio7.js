var numLim=parseInt(prompt("Ingrese un numero limite positivo"));
var num1=parseInt(prompt("ingrese un numero"));


var suma=0;
if(numLim>num1) {

do {
    var num2=parseInt(prompt("ingrese otro numero "));    

    var suma=suma+num2;
    
} while (numLim>=suma+num1);

}else{alert("el numero "+numLim+" ha sido superado")};
alert("el numero "+numLim+" ha sido superado");
