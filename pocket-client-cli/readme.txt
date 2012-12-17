
Datamodel

Group - Top-level category ('Logins', 'Bank Accounts'...)
- Id
- Title
- Icon 

Entry - Entry within a category (for the group 'Logins', could be 'Google', 'Amazon'...)
- Id
- Title
- Notes
- Group_id

GroupField - Template for Field associated with Group ('Username', 'Password', 'PIN'...)
- Id
- Title
- Group_id
- Hidden


Field - ('Username', 'Password'...)
- Id
- Title
- Value
- Entry_id
- Group_id
- Groupfield_id
- Hidden


GroupField          Group           Entry           Field
----------------------------------------------------------------
