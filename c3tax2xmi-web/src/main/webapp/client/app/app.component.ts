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
        let data = json.map(obj => new Category(obj.name));
        data.sort((a, b) => a.name.localeCompare(b.name));
        return data;
    }
    
    private createSubjects(json: any, level = 0) {
        if(json) {
            let data = json.map(obj => new Subject(obj.name, this.createSubjects(obj.children, level + 1), level < 3));
            data.sort((a, b) => a.name.localeCompare(b.name));
            return data;
        } else {
            return [];
        } 
    }
     
    export() {
        window.open("../service/export", "_blank");
    }
}
