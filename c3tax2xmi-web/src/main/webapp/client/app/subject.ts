export class Subject{
    
    id: string;
    name: string;
    children: Subject;
    catgories: Array;
        
    expanded:boolean;
    checked:boolean;
    highlighted: boolean;

    constructor(id, name, children, categories, expanded = false) {
        this.id = id;
        this.name = name;
        this.children = children;
        this.categories = categories;
        this.expanded = expanded;
        this.checked = false;
        this.highlighted = false;
    }
    
    toggle(){
        this.expanded = !this.expanded;
    }
    
    check(){
        let newState = !this.checked;
        this.checked = newState;
        this.checkRecursive(newState);
    }
    
    checkRecursive(state){
        this.children.forEach(d => {
            d.checked = state;
            d.checkRecursive(state);
        });
    }
    
 }