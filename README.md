# housing-app

Housing-app is developed to cater the needs of the users who own more than one house

The App is developed using Spring-Boot. Once the App starts up, the API documentation can be found in the below URL

http://localhost:8080/swagger-ui.html#/

THis App has multipe end-points defining the aspect of what the resource does

The app has 3 main resources
1) UserController
2) HouseController
3) RentController

UserController is the REST controller which is used for defining the end points for the user. It has end points which does the below things
1) A service to add a new user
2) A service to update the current user
3) A service to viw all the existing users
4) A service to fetch the user based on the user id
5) A service to delete the user


HouseController is the REST controller where the user can store or have the information aboout his houses. It has end points which does the below things

1) A service to add a new house for the user
2) A service to update the house
3) A service to viw all the existing houses of the user . He can view the houses that are rented and those are available
4) A service to fetch the house based on the house id
5) A service to delete the house for a user // The case where in user has sold the house and the house is no longer his own

RentController is the REST controller where the user can add tenant to the houses he own. It has end points which does the below things

1) A service to add a new tenant to the house
2) A service to update the tenant details // The user can make the tenant inactive , for some reason
3) A service to delete the tenant for a user // The case where in user has sold the house and the house is no longer his own
