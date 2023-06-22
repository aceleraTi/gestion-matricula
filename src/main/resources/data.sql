insert into semestres_academicos(numero,anio,fecha_inicio,fecha_fin)
values
(1,2019,"2019-01-26","2019-06-03"),
(2,2019,"2019-07-26","2019-12-03"),
(3,2021,"2021-02-26","2021-12-03"),
(4,2021,"2021-02-27","2021-12-03"),
(5,2022,"2022-02-27","2022-06-03"),
(6,2022,"2022-02-27","2022-06-03"),
(7,2023,"2023-02-27","2023-06-03"),
(8,2023,"2023-02-27","2023-06-03")
;


insert into cursos(id_materia,id_profesor,id_semestre_academico,grupo, estado)
values
(1,5,6,1,"En Curso"),
(3,6,7,2,"En Curso"),
(4,4,8,3,"En Curso")
;

insert into horarios (hora_inicio,hora_fin,dia,link,id_curso)
values
('09:00','12:00','viernes','https://calculoI',1),
('14:00','15:00','jueves','https://Etica',2),
('14:00','17:00','lunes','https://calculoII',3)
;




insert into estudiantes_pensums(id_estudiante,id_pensum)
values
(7,3),
(8,5)
;


