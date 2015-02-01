
    create table finance_account (
        id integer not null auto_increment,
        accountIdentifier varchar(255) not null,
        alias varchar(255) not null,
        primary key (id)
    );

    create table finance_stats_monthly (
        id integer not null,
        balance integer not null,
        month integer not null,
        totalIn integer not null,
        totalOut integer not null,
        totalTxs integer not null,
        totalTxsIn integer not null,
        totalTxsOut integer not null,
        year integer not null,
        primary key (id)
    );

    create table finance_stats_overview (
        id integer not null,
        totalBalance integer not null,
        totalIn integer not null,
        totalOut integer not null,
        totalTxs integer not null,
        primary key (id)
    );

    create table finance_txs (
        id integer not null auto_increment,
        amount integer not null,
        details varchar(255),
        epochDateTime bigint not null,
        title varchar(255),
        type integer not null,
        account_id integer not null,
        primary key (id)
    );

    create table projects (
        id integer not null auto_increment,
        description varchar(255),
        status integer not null,
        thumbnailUrl varchar(255) not null,
        title varchar(255) not null,
        urlPart varchar(255) not null,
        primary key (id)
    );

    alter table finance_account 
        add constraint UK_38e19rhco5b9srwfc4484n5q9  unique (accountIdentifier);

    alter table projects 
        add constraint UK_e3fph03q0pgspewue6bs1irt9  unique (urlPart);

    alter table finance_txs 
        add constraint FK_6g8v65lxm983ay5ouldatm987 
        foreign key (account_id) 
        references finance_account (id);
