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

function execute() {
    this.dog = new Dog();
    this.getDogInfo = getDogInfo;

    function getDogInfo() {
        this.dog.breed="Maltese";
        this.dog.size="Small";
        this.dog.age=2;
        this.dog.color="white";

        return this.dog.getInfo();
    }
}


var exe = new execute();

console.log(exe.getDogInfo());