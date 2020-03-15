var status = rs.status();
if (status.errmsg === 'no replset config has been received') {
    rs.initiate();
}
for (var i = 1; i <= param; i++) {
    if (i!==1)
        rs.add(folder+"_statistics-mongodb-node_" + i + ":27085");
}
cfg = rs.conf();
cfg.members[0].host = folder+"_statistics-mongodb-node_1:27085";
rs.reconfig(cfg);
