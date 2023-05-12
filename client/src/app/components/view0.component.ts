import { Component,OnInit} from '@angular/core';
import { ApiServiceService } from '../service/api-service.service';
import { Bundle } from '../model';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component implements OnInit {

  bundles!:Bundle[]

  constructor(private apiSvc:ApiServiceService) {}

  ngOnInit(): void {
      this.apiSvc.getBundles().then(
        (result) =>{
          this.bundles = result
        }
        
      )
  }
}
