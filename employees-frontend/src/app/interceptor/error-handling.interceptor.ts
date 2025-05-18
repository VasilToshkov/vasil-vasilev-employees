
import { HttpErrorResponse, HttpInterceptorFn, HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';
import { ToastService } from '../services/toast.service';



export const errorHandleInterceptor: HttpInterceptorFn = (req, next) => {
  const toastService = inject(ToastService);
  return next(req).pipe(
    //  tap((r) => {
    //   if(r instanceof HttpResponse) {
    //     switch (r.status) {
    //             case 204:
    //               toastService.showInfo("No data!", r.status.toString());
    //               break;
    //           }
    //         }
    //     }),
    catchError((error: HttpErrorResponse) => {
      switch (error.status) {
        case 404:
          toastService.showError("Page not found!", error.status.toString());
          break;
        case 409:
          toastService.showError("Username or Email is taken!", error.status.toString());
          break;
        case 500:
          toastService.showError("fatal error!", error.status.toString());
          break;
        default:
          toastService.showError(error.status.toString(), error.error);

      }
      return throwError(() => error.error);
    })
  );
};
