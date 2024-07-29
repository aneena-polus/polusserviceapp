import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Login } from '../../login/Login';
import { DataService } from '../../data.service';
import { Signup } from '../../signup/Signup';

@Component({
	selector: 'app-user-home',
	templateUrl: './user-home.component.html',
	styleUrl: './user-home.component.css'
})

export class UserHomeComponent {

    loggedInUser: Login = {} as Login;
    role: string = '';
    firstname: string = this.loggedInUser.firstname ?? '';
    isEditClicked: boolean = false;
	activeStatus: string = 'createTicket';

	constructor( public dialog: MatDialog,
                 private _data_service: DataService ) {}

    ngOnInit(): void {
        this.loggedInUser = this._data_service.getLoggedInUser();
        if ( this.loggedInUser && this.loggedInUser.roles ) {
			this.loggedInUser.roles.forEach( element => {
                element.roleId === 2 ? this.role='admin': null;
        	});
      	}
    }

    public editUserDetails(): void {
        this.isEditClicked = true;
        // const USERRO: Signup = {
        //     employeeId: this.loggedInUser.employeeId,
        //     firstname: ,
        //     lastname: ,
        //     email: ,
        //     designation: ,
        //     state: ,
        //     countryCode: ,
        //     phoneNumber: ,
        //     username: this.loggedInUser.username,
        //     password: this.loggedInUser.password
        // }
    }

    public setActiveStatus( status: string ): void {
        this.activeStatus = status;
    }

}
