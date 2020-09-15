let numbers = [2, 5, 12, 13, 15, 18, 22];
//ここに答えを実装してください。↓↓↓
for( let i =0; i<numbers.length; i++){
    if (numbers[i] % 2 ===0){
        isEven(numbers[i])
    }
}
function isEven(num) {
    console.log(num + 'は偶数です');
}

class car {
    constructor(gas,Num){
        this.gas=gas
        this.Num=Num;
    }

    getNumGas(){
        console.log(`ガソリンは${this.gas}です。ナンバーは${this.Num}です`);
    }
}

let honnda = new car("○○","△△");
honnda.getNumGas();

   
