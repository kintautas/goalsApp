import { Component } from '@angular/core';
import { HttpModule, JsonpModule } from '@angular/http';
import { Http, Response }          from '@angular/http';
import { Injectable }              from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Observable } from 'rxjs/Observable';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
@Injectable()
export class AppComponent {
  private myUrl = 'http://localhost:8080/restful_war_exploded/helloworld/';
  title = 'Goals';
  goals = [];
  edit = [];
  editable = [];
  data;
  changedValue;
  message = "";
  msg = false;
  constructor (private http: Http) {}

  getGoals() {
    this.http.get(this.myUrl).subscribe(res => {
      this.goals = res.json().goals;
      this.msg = true;
      this.message = "Goals have been loaded!";

    })
  }

  postGoals() {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    this.http.post(this.myUrl, {goals: this.goals} ,options).subscribe(result => {
      this.msg = true;
      this.message = "Goals have been saved!";
    });
  }
  addGoal(goal:string) {
    this.goals.push(goal);
    this.editable.push(false);
    this.msg = false;

  }

  changeGoal(i,goal) {
    this.goals[i] = goal;
    this.msg = false;
  }


  private extractData(res: Response) {
    let body = res.json();
    console.log(res.json());
    return body.data || { };
  }

  private handleError (error: Response | any) {
    // In a real world app, you might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.log(errMsg);
    return Observable.throw(errMsg);
  }

  getHeroes(): Observable<String[]> {
    return this.http.get(this.myUrl)
      .map(this.extractData)
      .catch(this.handleError);
  }

  saveData() {
      this.postGoals();
  }

  getData() {
    this.getGoals();
  }

  clearData() {
    this.goals = [];
    this.msg = false;
  }




}
