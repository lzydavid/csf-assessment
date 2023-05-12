import { Component,ElementRef,OnInit, ViewChild } from '@angular/core';
import { FormBuilder,FormGroup,Validators} from '@angular/forms';
import { BundleId } from '../model';
import { ApiServiceService } from '../service/api-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component implements OnInit{

  @ViewChild('file') file!:ElementRef

  form!:FormGroup
  f!:File
  bundleId!:BundleId

  constructor(private fb:FormBuilder,private apiSvc:ApiServiceService,private router:Router) {}
  
  ngOnInit(): void {
      this.form=this.createForm()
  }

  onSubmit(){
    const name = this.form.value['name']
    const title = this.form.value['title']
    const comment = this.form.value['comment']
    
    console.info(this.file.nativeElement.files[0])
    const file = this.file.nativeElement.files[0]
  
    const formData = new FormData()
    formData.set('name',name)
    formData.set('title',title)
    formData.set('comment',comment)
    formData.set('file',this.file.nativeElement.files[0])

    this.apiSvc.upload(formData).then(
      (result) =>{
        this.bundleId=result
        console.info(this.bundleId)
        const id =this.bundleId.bundleId

        this.router.navigate(['/view2',id]);
      }
    )
    
  }
  createForm() {
    return this.fb.group({
      name:this.fb.control<string>('dav',Validators.required),
      title:this.fb.control<string>('stu',Validators.required),
      comment:this.fb.control<string>('hi'),
      'file':this.fb.control(null,Validators.required)
    })
  }
}
