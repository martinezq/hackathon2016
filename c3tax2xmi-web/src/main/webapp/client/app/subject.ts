export class Subject{
    name: string;
    children: Subject;

    expanded:boolean;
    checked:boolean;

    constructor(name, children, expanded = false) {
        this.name = name;
        this.children = children;
        this.expanded = expanded;
        this.checked = false;
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
        })
    }
}