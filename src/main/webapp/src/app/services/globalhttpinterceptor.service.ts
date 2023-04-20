import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GlobalhttpinterceptorService implements HttpInterceptor {

  constructor() {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log("Passed through the interceptor in request");

    return next.handle(request)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMsg;
          if (error.error instanceof ErrorEvent) {
            window.alert(error.error.message)
            errorMsg = `Error: ${error.error.message}`;
          } else {
            window.alert(error.error.message)
            errorMsg = `Error Code: ${error.status},  Message: ${error.message}`;
          }
          return throwError(errorMsg);
        })
      )
  }
}
