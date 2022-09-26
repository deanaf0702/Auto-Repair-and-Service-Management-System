# CSC 540 - Project 1

## Milestone 1 - Report, Due October 3rd 9AM

### Requirements

1. Fill in the form for deciding team members as soon as possible.
2. A partial ER-Diagram covering the entity and relationship types and constraints that you have
   identified so far relevant to Employees, Stores and Services.
3. A translation of your partial E-R model into SQL.
4. Include a table that has two columns. One column has a phrase in the description document
   that you have identified as a constraint and the other column says how your model so far has captured
   the constraint e.g. foreign key. You do not need to include those that you have not captured yet since
   some constraints will require all of SQL to capture.
5. Also include up to 5 functional dependencies that you have identified so far.

### ER-Diagram Implementation Notes and Discussion

#### [LucidChart](https://lucid.app/lucidchart/89a67dbb-57a8-4f89-85eb-31f9cce9a078/edit?viewport_loc=-358%2C364%2C5778%2C2299%2C0_0&invitationId=inv_e76116b6-835c-4809-97cf-06dd0d51f0ae#) for viewing ER diagram in addition to the [svg](./Database_ER_Diagram.svg) housed within the repo

#### General Notes

- In the diagram I constructed, I tended to provide "somewhat redundant" information to the
  model when the description explicitly stated that said information is to be stored. Not sure if
  this should be revisited / is unneeded.

  - Example: The employee entity has a role attribute in addition to the IS-A hierarchy defined under
    employees since the description specifies that we store their role

- Additionally, I have added a few relationship constraints (e.g. 0 or more, at most 1, exactly 1),
  however, I'm sure there are constraints which exist that aren't described in my current model.

#### Service Center Details

- All service centers are open M-F w/ same hours, only difference is between whether or not
  service is open on Saturdays. As such, Model including schedule type and business hours seems
  a bit verbose to me. Instead, can be captured with single boolean attribute, open_on_saturday,
  on service center

- How to represent locally unique employee id as different service centers may reuse employee ids?
  What does that look like in ER diagram? Does that change model? Is it fine to just use globally
  unique employee IDs as these by definition are locally unique.

- In my current ER diagram, storing wage attribute for mechanics may be unnecessary/redundant
  as mechanic_hourly_rate attribute already stores that information and is the source of truth for wages.

- In my current ER diagram, I haven't yet attempted to model maintenance services "bundles" / schedules.
  If you already have an model for this that's great.

#### Customer Details

- Currently, as I've modeled customers, there is customer entity and vehicle entity with
  'Has' relationship between them along with a 'Belongs To' relationship between customer and
  service center. And finally, a relationship between customer and the receptionist who
  added them.

#### Car Service Scheduling

- In terms of modeling the services, I'm not sure on the best approach here. At the moment,
  I'm using a service_type attribute (e.g. 'maintenance', 'repair', or 'both') on Services to
  distinguish between them.

- I'm not sure if an IS-A hierarchy would make sense in terms of modeling different types of services.
  What unique attributes would be on each of the specific subclasses? Should we use an IS-A hierarchy
  if there are no unique attributes on the subclasses?

- Should there be a service request entity? If so, what would the key be? It would be plausible
  to have multiple service requests which are the same (i.e. the customer needs same maintenance
  as they had received in the past). This would then create tuples in the table which we can't
  necessarily guarantee uniqueness for.

- Should there be some sort of schedule entity which stores time slots, the services requested,
  and the mechanic issued to work on them? Or should this be handled within the application
  level logic?
