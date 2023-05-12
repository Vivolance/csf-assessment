import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-view-one',
  templateUrl: './view-one.component.html',
  styleUrls: ['./view-one.component.css']
})
export class ViewOneComponent implements OnInit {
  form!: FormGroup;

  constructor(private formBuilder: FormBuilder, private http: HttpClient) {
  }

  ngOnInit(): void {
    this.createForm();
  }

  private createForm(){
    this.form = this.formBuilder.group({
      name: ['', Validators.required],
      title: ['', Validators.required],
      comments: '',
      archive: [null, Validators.required]
    });
  }

  handleFileInput(event: any) {
    const file = event.target.files[0];
    // Assign the file to the 'archive' field in the form
    this.form.patchValue({ archive: file });
    // Mark the 'archive' field as touched to trigger validation
    this.form.get('archive')?.markAsTouched();
  }

  isFormControlInvalid(controlName: string): boolean {
    const control = this.form.controls[controlName];
    return control.invalid && control.touched;
  }

  submitForm() {
    if (this.form.valid) {
      // Prepare the form data
      const formData = new FormData();
      formData.append('name', this.form.value.name);
      formData.append('title', this.form.value.title);
      formData.append('comments', this.form.value.comments);
      formData.append('archive', this.form.value.archive);

      // Prepare the HTTP headers
      const headers = new HttpHeaders()
      
      headers.set("Content-Type", "multipart/form-data");
      headers.set("Accept", "application/json");

      const requestOptions = {
        headers: headers,
        timeout: 300000
      };

      // Send the HTTP POST request
      this.http.post('http://localhost:8080/upload', formData, requestOptions).subscribe(
        response => {
          console.log('Form submitted successfully');
          console.log(response);
        },
        error => {
          console.log('Error submitting form');
          console.error(error);
        }
      );
    } else {
      // Display error messages or perform any other necessary actions
      console.log('Form contains errors');
    }
  }
}