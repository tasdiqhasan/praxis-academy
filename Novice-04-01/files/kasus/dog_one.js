function Dog() {
    this.breed = '',
    this.size= '',
    this.age = '',
    this.color = '';
    this.getInfo = getInfo;
    
    function getInfo() {
        return ("Breed is: "+this.breed+" Size is:"+this.size+" Age is:"+this.age+" color is: "+this.color);
    }
}


var Dogo = new Dog();
    Dogo.breed="Maltese";
    Dogo.size="Small";
    Dogo.age=2;
    Dogo.color="white";

console.log(Dogo.getInfo());