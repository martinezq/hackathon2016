export class Category{
    name: string;
    id: string;

    checked:boolean;

    constructor(id, name) {
        this.id = id;
        this.name = name;
        this.checked = false;
    }
    
    toggle(){
        let newState = !this.checked;
        this.checked = newState;
    }
    
}