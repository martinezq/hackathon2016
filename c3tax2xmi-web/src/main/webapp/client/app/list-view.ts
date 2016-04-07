import {Component, Input} from 'angular2/core';
import {Category} from "./category";

@Component({
    selector: 'list-view',
    templateUrl: './app/list-view.html'
})
export class ListView {
    @Input() categories: Array<Category>;
    
    allChecked = false;
    
    toggleAll() {
        this.allChecked = !this.allChecked;
        
        this.categories.forEach(cat => cat.checked = this.allChecked);
    }
}