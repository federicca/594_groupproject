# MCIT594 Group Project Report
#### Sean Bivins, Federica Pelzel


## Additional Features
We have created 2 additional features. We focused on identifying areas of the data that hadn't been explored in order to showcase another dimension of the project.

#### Lookup parking violations for a specific car
The user will input a car id, and the program will return a description of all parking violations and total fines due for that car. In order to make this feature more efficient cars were not only added to the ParkingViolation object and subsequently to the ZipCode, but were also added to a TreeSet of Car elements, that are sorted by CarID. This allows for a more efficient iteration when searching for a specific Car.


## Data Structures

#### TreeMap<Integer, ZipCode>
When analyzing the instructions we realized that most methods in the data analysis requirements start by calling on a ZipCode. To that effect, we decided to create a TreeMap that holds the zipcode number as the key, and a ZipCode object as the value. TreeMap get/put/containsKey() operations are O(1), which seemed like the most efficient we could get withing our needs. ZipCode objects hold ArrayLists of ParkingViolations and Properties, that allow for efficient data retrieval. We also chose a TreeMap even though it's a little bit slower than other maps, because of it's sorted nature. For returning data or generating reports, having things sorted by ZipCode seemed aligned with the goals and needs of the assignment.

#### LinkedList of ParkingViolations in Car object
