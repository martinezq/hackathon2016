import {Component} from "angular2/core";
import {Http, HTTP_BINDINGS} from "angular2/http";
import {TreeView} from "./tree-view";
import {ListView} from "./list-view";
import {Subject} from "./subject";
import {Category} from "./category";

@Component({
    selector: "my-app",
    templateUrl: "app/app.component.html",
    providers: [HTTP_BINDINGS],
    directives: [TreeView, ListView]
})
export class AppComponent { 

    subjects: any;
    categories: any;
    
    constructor(private http: Http) {
        this.refresh();
    }
    
    refresh() {
        this.http.get("../service/categories").subscribe(
            data => this.categories = this.createCategories(data.json()),
            err => console.error(err)
        );

        this.http.get("../service/tree").subscribe(
            data => this.subjects = this.createSubjects(data.json(), 0),
            err => console.error(err)
        );
    }
    
    private createCategories(json: any) {
        let data = json.map(obj => new Category(obj.id, obj.name));
        data.sort((a, b) => a.name.localeCompare(b.name));
        return data;
    }
    
    private createSubjects(json: any, level = 0) {
        if(json) {
            let data = json.map(obj => new Subject(obj.id, obj.name, this.createSubjects(obj.children, level + 1), level < 3));
            data.sort((a, b) => a.name.localeCompare(b.name));
            return data;
        } else {
            return [];
        } 
    }
    
    selectedRoot(top: Subject) {
        if(top.checked) {
            return top;
         } else {
            for(let i=0; i<top.children.length; i++) {
                let child = this.selectedRoot(top.children[i]);
                if(child) {
                    return child;
                }    
            }
         }
    }
    
    selectedSubjects() {
        let result = [];
        
        let func = subject => {
            if(subject.checked) {
                result.push(subject.id);
            }
            
            subject.children.forEach(child => func(child));
        }
        
        this.subjects.forEach(s => {
            func(s);
        });
        
        return result;
    }
    
    selectedCategories() {
        let selectedCategories = [];
        this.categories.forEach(cat => {
            if(cat.checked) {
                selectedCategories.push(cat.id);
            }    
        });
        return selectedCategories;
    }
    
    export() {
        
        let selectedRoot = this.selectedRoot(this.subjects[0]);
        
        if(selectedRoot === undefined) {
            alert("Select root element");
            return;    
        }
        
        let req = {
            subjects: this.selectedSubjects(),
            categories: this.selectedCategories()    
        }
        
        console.log(req);
        
        
        this.http.post("../service/export/selected", JSON.stringify(req)).subscribe(
            data => {
                console.log(data.text());
                window.open("../service/export/get/" + data.text(), "_blank");
            },
            err => console.error(err)
        ); 
    }
}
