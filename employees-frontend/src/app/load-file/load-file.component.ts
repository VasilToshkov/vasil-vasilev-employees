import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { FileUploadModule } from 'primeng/fileupload';
import { PairModel } from './model/pair.model';
import { CommonModule } from '@angular/common';
import { ToastService } from '../services/toast.service';
import { LoadFileService } from './service/load-file.service';
import { finalize, tap } from 'rxjs';

@Component({
  selector: 'app-load-file',
  standalone: true,
  imports: [
    CommonModule,
    FileUploadModule,
    ButtonModule,
    TableModule
  ],
  templateUrl: './load-file.component.html'
})
export class LoadFileComponent implements OnInit{

  selectedFile: File | null = null;
  fileSelected: boolean|undefined;
  pair: PairModel;
  searching: boolean;

  constructor(
    private loadFileService: LoadFileService,
    private toastSerivce: ToastService
  ) {}

  ngOnInit(): void {
  }

  onFileSelected(event: any, fileUpluader: any): void {
    this.fileSelected = false;
    this.selectedFile = null;
    const file: File = event.currentFiles[0];
    this.fileSelected = true;
    if (file && file.type === 'text/csv') {
      this.selectedFile = file;
    } else {
      this.selectedFile = null;
    }
    fileUpluader.clear();
    this.sendFile()
  }

  sendFile(): void {
    if (!this.selectedFile) {
      this.toastSerivce.showInfo('No file selected!')
      return;
    }

    this.searching = true;
    this.pair = null;

    const formData = new FormData();
    formData.append('file', this.selectedFile, this.selectedFile.name);
    this.loadFileService.getBestPair(formData).pipe(
      finalize(() => {
        this.searching = false;
      })
    ).subscribe(
      (res: PairModel) => {
        this.pair = res;
        if (this.pair) {
          this.toastSerivce.showInfo('Success!')
        }
        else {
          this.toastSerivce.showInfo("No data!")
        }

      }
    );
  }

  clearTable() {
  this.pair = null;
  }
}
