-- ============================================================
-- INSERTS gestiona_bd
-- admin/admin | profesor/pass | estudiante/pass | tutor/pass
-- Resto de usuarios: pass
-- ============================================================

INSERT INTO `persona` (`id_persona`, `nombre`, `apellidos`, `email`, `telefono`, `usuario`, `contraseña`, `perfil`) VALUES
(1,  'Admin',   'Sistema',   'admin@gestiona.com',    '000000000', 'admin',      '$2a$12$Xa.QXfwIZzZ2bbKVfVxH7.98k2lmf/9RO7ZY3atU4WWqmQqi/Apju', 'ADMINISTRADOR'),
(2,  'Joel',    'Miller',    'joel@gestiona.com',     '666111001', 'profesor',   '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'PROFESOR'),
(3,  'Nathan',  'Drake',     'nathan@gestiona.com',   '666111002', 'nathan',     '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'PROFESOR'),
(4,  'Lara',    'Croft',     'lara@gestiona.com',     '666111003', 'lara',       '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'PROFESOR'),
(5,  'John',    'Shepard',   'shepard@gestiona.com',  '666111004', 'shepard',    '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'PROFESOR'),
(6,  'Cave',    'Johnson',   'cave@aperture.es',      '985001001', 'tutor',      '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'TUTOREMPRESA'),
(7,  'Eli',     'Vance',     'eli@blackmesa.es',      '985002001', 'eli',        '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'TUTOREMPRESA'),
(8,  'Robert',  'House',     'house@robco.es',        '985003001', 'house',      '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'TUTOREMPRESA'),
(9,  'Ellie',   'Williams',  'ellie@gestiona.com',    '611001001', 'estudiante', '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'ESTUDIANTE'),
(10, 'Cloud',   'Strife',    'cloud@gestiona.com',    '611001002', 'cloud',      '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'ESTUDIANTE'),
(11, 'Aloy',    'Nora',      'aloy@gestiona.com',     '611001003', 'aloy',       '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'ESTUDIANTE'),
(12, 'Arthur',  'Morgan',    'arthur@gestiona.com',   '611001004', 'arthur',     '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'ESTUDIANTE'),
(13, 'Geralt',  'Rivia',     'geralt@gestiona.com',   '611001005', 'geralt',     '$2a$12$VXTSsF.hF/SHkK2DgeWLeOLwgW2mkJeyFQmcRmN9pPsLGnDmI/mdi', 'ESTUDIANTE');

INSERT INTO `administrador` (`id_persona`) VALUES (1);

INSERT INTO `profesor` (`id_persona`) VALUES (2), (3), (4), (5);

INSERT INTO `empresa` (`id_empresa`, `nombre`, `direccion`, `telefono`) VALUES
(1, 'Aperture Science',  'Repulsion Gel Road, 7',      '985001000'),
(2, 'Black Mesa',        'Sector C Research Blvd, 15', '985002000'),
(3, 'RobCo Industries',  'Mojave Wasteland, 101',      '985003000');

INSERT INTO `tutor_empresa` (`id_persona`, `fk_empresa_id`) VALUES
(6, 1),
(7, 2),
(8, 3);

INSERT INTO `curso` (`id`, `codigo`, `ciclo`, `tipo_curso`, `fk_profesor`) VALUES
(1, '2VIFC302',  2, 'DAM',            2),
(2, '1VIFC302',  1, 'DAM',            NULL),
(3, '2IFC303',   2, 'DAW_DIURNO',     3),
(4, '1IFC303',   1, 'DAW_DIURNO',     NULL),
(5, '2VIFC303',  2, 'DAW_VESPERTINO', 4),
(6, '1VIFC303',  1, 'DAW_VESPERTINO', NULL),
(7, '2@IFC303',  2, 'DAW_VIRTUAL',    5),
(8, '1@IFC303',  1, 'DAW_VIRTUAL',    NULL);

INSERT INTO `estudiante` (`id_persona`, `numero_ss`, `fk_curso`) VALUES
(9,  '111111111111', 1),
(10, '222222222222', 1),
(11, '333333333333', 1),
(12, '444444444444', 1),
(13, '555555555555', 1);

INSERT INTO `fct` (`id_fct`, `fecha_inicio`, `fecha_fin`, `periodo`, `fk_estudiante`, `fk_tutor`) VALUES
(1, '2026-03-02', '2026-05-29', 'ORDINARIO',      9,  6),
(2, '2026-03-02', '2026-05-29', 'ORDINARIO',      10, 7),
(3, '2026-06-15', '2026-08-14', 'EXTRAORDINARIO', 13, 8);
