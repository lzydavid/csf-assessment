import { Component,OnInit} from '@angular/core';
import { ApiServiceService } from '../service/api-service.service';
import { Bundle } from '../model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component implements OnInit {

  bundles!:Bundle[]

  constructor(private apiSvc:ApiServiceService,private router:Router) {}

  ngOnInit(): void {
      this.apiSvc.getBundles().then(
        (result) =>{
          this.bundles = result
          console.info(this.bundles)
        }
        
      )
  }

  async display(id:string){
    console.info(id)
    this.router.navigate(['/view2',id])
  }
}
