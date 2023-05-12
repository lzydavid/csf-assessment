import { HttpClient,HttpHeaders,HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Bundle, BundleId, Upload } from '../model';
import { firstValueFrom, identity } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {

  private SERVER_POST_URL= '/upload'
  private SERVER_GET_URL = '/bundle'

  constructor(private httpClient:HttpClient) { }

  upload(formData:FormData):Promise<BundleId>{

    const headers = new HttpHeaders().set('Content-Type','multipart/form-data')
    return firstValueFrom(this.httpClient.post<BundleId>(this.SERVER_POST_URL,formData))
  }

  getImages(bundleId:string):Promise<Upload> {

    const url = this.SERVER_GET_URL+'/'+bundleId

    return firstValueFrom(this.httpClient.get<Upload>(url))
  }

  getBundles():Promise<Bundle[]> {
    return firstValueFrom(this.httpClient.get<Bundle[]>(this.SERVER_GET_URL))
  }
}
