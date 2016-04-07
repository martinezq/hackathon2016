import {Component, Input, Output, EventEmitter} from 'angular2/core';
import {Category} from "./category";

@Component({
    selector: 'list-view',
    templateUrl: './app/list-view.html'
})
export class ListView {
    @Input() categories: Array<Category>;
    @Output() onChange = new EventEmitter();
    
    allChecked = false;
    
    toggleAll() {
        this.allChecked = !this.allChecked;
        this.categories.forEach(cat => cat.checked = this.allChecked);
        this.onChange.emit(this.selectedCategories());
    }
    
    toggle(cat: Category) {
        cat.toggle();
        this.onChange.emit(this.selectedCategories());
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
}