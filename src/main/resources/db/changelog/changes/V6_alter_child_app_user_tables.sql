alter table app_user drop column password;
alter table app_user add constraint unique_phone UNIQUE(phone_number);