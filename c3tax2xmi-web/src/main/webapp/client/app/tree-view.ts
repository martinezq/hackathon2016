import {Component, Input, Inject, forwardRef} from 'angular2/core';
import {Subject} from './subject';
import {AppComponent} from "./app.component";

@Component({
    selector: 'tree-view',
    templateUrl: './app/tree-view.html',
    directives: [TreeView]
})
export class TreeView {
    @Input() subjects: Array<Subject>;
    
    app: AppComponent;
    
    constructor(@Inject(forwardRef(() => AppComponent)) app: AppComponent) {
        this.app = app;
        if(this.app.tree === undefined) {
            this.app.tree = this;
        }
    }
    
    highlight(selectedCategories: any) {
        let func = s => {
            s.highlighted = false;
            for(let cat of selectedCategories) {
                if(s.categories.indexOf(cat) > -1) {
                    s.highlighted = true;
                }
            }
                       
            s.children.forEach(c => func(c));
        };
        
        this.subjects.forEach(s => func(s));
    }
}