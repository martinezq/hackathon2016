import {Component, Input} from 'angular2/core';
import {Subject} from './subject';

@Component({
    selector: 'tree-view',
    templateUrl: './app/tree-view.html',
    directives: [TreeView]
})
export class TreeView {
    @Input() subjects: Array<Subject>;
}