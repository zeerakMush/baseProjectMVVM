package com.zk.base_project.network.repositeries

import com.zk.base_project.database.doa.DummyDoa
import com.zk.base_project.network.services.DummyApiServices
import javax.inject.Inject

class DummyRepository @Inject constructor(
    dummyDoa: DummyDoa,
    dummyApiServices: DummyApiServices
): BaseRepository() {
}