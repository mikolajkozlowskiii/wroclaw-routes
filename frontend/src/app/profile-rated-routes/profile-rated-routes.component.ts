import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Subscription } from "rxjs";
import { FormControl } from "@angular/forms";
import { Router } from "@angular/router";
import { MatTableDataSource } from "@angular/material/table";
import { MatPaginator } from "@angular/material/paginator";
import { IRouteDetails, IRouteRatedDetails, IRouteSavedDetails } from '../routes-list/route';
import { RouteService } from '../routes-list/route.service';
@Component({
  selector: 'pm-profile-rated-routes',
  templateUrl: './profile-rated-routes.component.html',
  styleUrls: ['./profile-rated-routes.component.css']
})
export class ProfileRatedRoutesComponent implements OnInit, OnDestroy, AfterViewInit {

  Tags = new FormControl();
  toppingList: string[] = ['FIRST TIME IN WROCLAW', 'NOT MANY TOURIST', 'NATURAL', 'PEACEFUL', 'MODERN', 'LOCALS'];
  pageTitle: string = 'Routes List';
  sub!: Subscription;
  routes: IRouteRatedDetails[] = [];
  errorMessage = '';
  dataSource = new MatTableDataSource<IRouteRatedDetails>([]);
  displayedRoutes: IRouteRatedDetails[] = []; // New array for displayed routes

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private routeService: RouteService, private router: Router) {}

  ngOnInit(): void {
    console.log("on init works");
    this.sub = this.routeService.getUserRatedRoutes().subscribe({
      next: routes => {
        this.routes = routes;
        this.dataSource.data = this.routes;
        this.dataSource.paginator = this.paginator; 
        this.applyPaginatorSettings();
        console.log("saved: "+routes);
      },
      error: err => this.errorMessage = err
    });
    console.log('teeeeeest');
    console.log(this.routes.map(s => s.routeDetails.tags).values());
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  ngAfterViewInit() {
    if (this.paginator) {
      this.dataSource.paginator = this.paginator;
      this.applyPaginatorSettings();
    }
  }

  applyPaginatorSettings() {
    if (this.dataSource.paginator) {
      this.dataSource.paginator.pageSize = this.paginator.pageSize;
      this.dataSource.paginator.pageIndex = this.paginator.pageIndex;
      this.dataSource.paginator.length = this.displayedRoutes.length;
    }
  
    let routesToDisplay: IRouteRatedDetails[];
  
    if (this.selectedButton === 'group') {
      // Sort by usersRatings.average_rating for 'group'
      this.routes.sort((a, b) => b.rate - a.rate);
    } else if (this.selectedButton === 'verified') {
      // Sort by guidesRatings.average_rating for 'verified'
      this.routes.sort((a, b) => b.rate - a.routeDetails.guideUsersRatings.average_rating);
    } else if (this.selectedButton === 'trending') {
      // Sort by created_at ascending for 'trending'
      this.routes.sort((a, b) => new Date(a.rated_at).getTime() - new Date(b.rated_at).getTime());
    } else if (this.selectedButton === 'calendar') {
      // Sort by created_at descending for 'calendar'
      this.routes.sort((a, b) => new Date(b.rated_at).getTime() - new Date(a.rated_at).getTime());
    }
  
    const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
    const endIndex = startIndex + this.paginator.pageSize;
  
    // Use slice to create a new array based on the current paginator settings
    routesToDisplay = this.routes.slice(startIndex, endIndex);
    this.displayedRoutes = [...routesToDisplay];
  
    this.dataSource.data = this.routes;
  }

  onPageChange(event: any) {
    this.applyPaginatorSettings();
  }

  onPageSizeChange(event: any) {
    this.applyPaginatorSettings();
  }

  onRatingClicked(message: string): void {
    this.pageTitle = 'Routes list: ' + message;
  }

  navigateToRoute(routeId: number) {
    this.router.navigate(['routes-list', routeId]);
  }

  selectedButton: string | null = 'verified';

  toggleButton(button: string): void {
    if (this.selectedButton === button) {
      this.selectedButton = null;
    } else {
      this.selectedButton = button;
      this.handleButtonClick(button);
    }
  }

  handleButtonClick(button: string): void {
    console.log(`Button '${button}' is toggled`);
    console.log(this.Tags)
    if (this.selectedButton === 'group') {
      console.log("groupssss")
      // Sort by usersRatings.average_rating for 'group'
      this.displayedRoutes.sort((a, b) => b.rate - a.rate);
    } else if (this.selectedButton === 'verified') {
      // Sort by guidesRatings.average_rating for 'verified'
      console.log("verified")
      this.displayedRoutes.sort((a, b) => b.routeDetails.guideUsersRatings.average_rating - a.routeDetails.guideUsersRatings.average_rating);
    } else if (this.selectedButton === 'trending') {
      console.log("trending")
      // Sort by created_at ascending for 'trending'
      this.displayedRoutes.sort((a, b) => new Date(a.rated_at).getTime() - new Date(b.rated_at).getTime());
    } else if (this.selectedButton === 'calendar-down') {
      // Sort by created_at descending for 'calendar'
      console.log("calendar")
      this.displayedRoutes.sort((a, b) => new Date(b.rated_at).getTime() - new Date(a.rated_at).getTime());
    }
    else if (this.selectedButton === 'calendar-up') {
      // Sort by created_at descending for 'calendar'
      console.log("calendar")
      this.displayedRoutes.sort((a, b) =>  new Date(a.rated_at).getTime() - new Date(b.rated_at).getTime() );
    }
  
  
  }
}
