import { Component, ViewChild, ElementRef } from '@angular/core';
import { Login, ServiceType, CreateTicket, ShowTicket } from './ServiceType';
import { Router } from '@angular/router';
import { DataService } from '../../data.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
    selector: 'app-create-ticket',
    templateUrl: './create-ticket.component.html',
    styleUrl: './create-ticket.component.css'
})

export class CreateTicketComponent {

    @ViewChild('collapseCreateTicket') collapseCreateTicket!: ElementRef;
    @ViewChild('ticketRequests') ticketRequests!: ElementRef;
    @ViewChild('buttonCreateTicket') buttonCreateTicket!: ElementRef;
    @ViewChild('buttonTicketReq') buttonTicketReq!: ElementRef;

    errorMap = new Map<string, string>();
    serviceTypes: ServiceType[] = [];
    createdTicket: ShowTicket | undefined;
    serviceType: number = 0;
    serviceDesc: string = '';
    employeeId: number | undefined;
    loggedInUser: Login = {} as Login;
    isFormSubmitted: boolean = false;

    constructor(private _data_service: DataService,
        private _snackbar: MatSnackBar,
        private _ROUTER: Router,) { }

    ngOnInit(): void {
        this.getServiceTypes();
        this.loggedInUser = this._data_service.getLoggedInUser();
        this.employeeId = this.loggedInUser.employeeId;
    }

    public getServiceTypes(): void {
        this._data_service.getServiceTypes().subscribe((data: ServiceType[]) => {
            this.serviceTypes = data.sort((a, b) => a.ticketType.localeCompare(b.ticketType));
        });
    }

    public createTicket(): void {
        this.errorMap.clear();
        !this.serviceType ? this.displayErrorMessage('ticketTypeMessage', 'Please select a ticket type.') : null;
        !this.serviceDesc ? this.displayErrorMessage('serviceDescMessage', 'Please enter description.') : null;
        const TICKETRO: CreateTicket = {
            ticketType: this.serviceType,
            ticketCreateBy: this.employeeId,
            ticketDescription: this.serviceDesc,
        };
        if (!this.errorMap.has('ticketTypeMessage') && !this.errorMap.has('serviceDescMessage')) {
            this._data_service.createTicket(TICKETRO).subscribe({
                next: (response: ShowTicket) => {
                    console.log(response);
                    this.serviceDesc = '';
                    this.createdTicket = response;
                    this.isFormSubmitted = true;
                    this.toggleAccordion();
                    response ? this.openCreateTicketMessage() : console.log('No response received');
                },
                error: (error) => {
                    console.error(error);
                },
            });
        }
    }

    private toggleAccordion(): void {
        const createTicketElement = this.collapseCreateTicket?.nativeElement;
        const ticketRequestElement = this.ticketRequests?.nativeElement;
        const buttonCreateTicketElement = this.buttonCreateTicket?.nativeElement;
        const buttonTicketRequestElement = this.buttonTicketReq?.nativeElement;
        buttonCreateTicketElement?.classList.add('collapsed');
        buttonTicketRequestElement?.classList.remove('collapsed');
        createTicketElement?.classList.remove('show');
        createTicketElement?.classList.add('collapse');
        ticketRequestElement?.classList.add('show');
        ticketRequestElement?.classList.remove('collapse');
    }

    private displayErrorMessage(key: string, value: string): void {
        this.errorMap.set(key, value);
    }

    private openCreateTicketMessage(): void {
        this._snackbar.open(`Ticket Created Successfully.`, undefined, {
            duration: 2000,
            panelClass: 'my-custom-snackbar'
        });
    }
}

