export class Category{
    name: string;

    checked:boolean;

    constructor(name) {
        this.name = name;
        this.checked = false;
    }
    
    check(){
        let newState = !this.checked;
        this.checked = newState;
    }
    
}