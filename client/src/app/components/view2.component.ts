import { Component,OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiServiceService } from '../service/api-service.service';
import { Upload } from '../model';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrls: ['./view2.component.css']
})
export class View2Component implements OnInit{

  upload!:Upload

  constructor(private router:Router,private activiatedRoute:ActivatedRoute,private apiSvc:ApiServiceService) {}

  ngOnInit(): void {
    const id = this.activiatedRoute.snapshot.params['id']

    this.apiSvc.getImages(id).then(
      (result) => {
        this.upload=result as Upload
      }
    )
  }
}
