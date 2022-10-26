import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../_services";
import {ActivatedRoute, Router} from "@angular/router";


@Component({templateUrl: 'about.component.html'})
export class AboutComponent implements OnInit {
    isProgramEnd: boolean;

    constructor(
        private authenticationService: AuthenticationService,
    ) {
        this.authenticationService.isEnd().subscribe(data => {
                this.isProgramEnd = false;
            }, error => {
                this.isProgramEnd = true;
            }
        )
    }

    ngOnInit() {

    }

    end() {
        this.authenticationService.end().subscribe(data => {
            this.isProgramEnd = true;
        })
    }

}

