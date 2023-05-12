import { Component,ElementRef,OnInit, ViewChild, ɵinternalCreateApplication } from '@angular/core';
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
  isFileUpload:boolean = false

  constructor(private fb:FormBuilder,private apiSvc:ApiServiceService,private router:Router) {}
  
  ngOnInit(): void {
      this.form=this.createForm()
  }

  onSubmit(){
    const name = this.form.value['name']
    const title = this.form.value['title']
    const comment = this.form.value['comment']

    const file = this.file.nativeElement.files[0]
  
    const formData = new FormData()
    formData.set('name',name)
    formData.set('title',title)
    formData.set('comment',comment)
    formData.set('file',this.file.nativeElement.files[0])

    this.apiSvc.upload(formData).then(
      (result) =>{
        this.bundleId=result
        const id =this.bundleId.bundleId

        this.router.navigate(['/view2',id]);
      }
    )   
  }
  fileSubmit(){
    this.isFileUpload=true
  }

  isFormInvalid() {
    const fn = this.form.get('name')?.valid
    const ft = this.form.get('title')?.valid
    if(fn){
      if(ft){
        if(this.isFileUpload){
          return false
        }
      }
    }
    return true;
  }


  createForm() {
    return this.fb.group({
      name:this.fb.control<string>('',Validators.required),
      title:this.fb.control<string>('',Validators.required),
      comment:this.fb.control<string>(''),
      'file':this.fb.control(null,Validators.required)
    })
  }
}
