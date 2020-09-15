let scores = [10,15,20,25]
console.log(scores)
for (let i=0; i< scores.length; i++ ){
    if (scores[i] % 2 === 0){
    console.log(scores[i]+"は偶数です")
    }
}

let car = {
    gas:20.5,
    num:1234
}
console.log(car);
console.log("ガソリンは"+ car.gas);
console.log("ナンバーは" + car.num);