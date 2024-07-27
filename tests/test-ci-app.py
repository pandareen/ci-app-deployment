import pytest
import json
import requests
import logging
import jsondiff
import os

logger = logging.getLogger(__name__)
logger.propagate = True

WEBAPP_HOSTNAME = os.environ['ci_app_host']

def test_get_all_jobs():
    logger.info("Getting all jobs")
    response = requests.get(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs")
    test_json = [{'id': 1, 'jobName': '30DE579E', 'status': 'L', 'createdAt': 1721433600000, 'updatedAt': 1722124800000, 'jobType': 'ops'}, {'id': 2, 'jobName': '2B70FD56', 'status': 'D', 'createdAt': 1721520000000, 'updatedAt': 1722297600000, 'jobType': 'reporting'}, {'id': 3, 'jobName': '74261488', 'status': 'D', 'createdAt': 1721606400000, 'updatedAt': 1722470400000, 'jobType': 'application'}, {'id': 4, 'jobName': 'E4FD6201', 'status': 'L', 'createdAt': 1721692800000, 'updatedAt': 1722643200000, 'jobType': 'ops'}, {'id': 5, 'jobName': '61472E7F', 'status': 'D', 'createdAt': 1721779200000, 'updatedAt': 1722816000000, 'jobType': 'reporting'}, {'id': 6, 'jobName': '3598D3C6', 'status': 'D', 'createdAt': 1721865600000, 'updatedAt': 1722988800000, 'jobType': 'application'}, {'id': 7, 'jobName': '5A02A577', 'status': 'L', 'createdAt': 1721952000000, 'updatedAt': 1723161600000, 'jobType': 'ops'}, {'id': 8, 'jobName': 'F3EBC01F', 'status': 'D', 'createdAt': 1722038400000, 'updatedAt': 1723334400000, 'jobType': 'reporting'}, {'id': 9, 'jobName': '42894AF5', 'status': 'D', 'createdAt': 1722124800000, 'updatedAt': 1723507200000, 'jobType': 'application'}, {'id': 10, 'jobName': '32BBAF6E', 'status': 'L', 'createdAt': 1722211200000, 'updatedAt': 1723680000000, 'jobType': 'ops'}]
    

    r = jsondiff.diff(sorted(test_json, key=lambda d: d['id']),sorted(response.json(), key=lambda d: d['id']))

    logger.info(f" response all jobs is {sorted(test_json, key=lambda d: d['id'])}")
    logger.info(f" test_json all jobs is {sorted(response.json(), key=lambda d: d['id'])}")
    assert r == {}


def test_create_job():
    logger.info("Creating new job")
    response = requests.put(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs", data=json.dumps({'jobName': 'NEW JOB', 'status': 'D', 'createdAt': 1721500200000, 'updatedAt': 1722277800000, 'jobType': 'reporting'})
                            , headers={'Content-Type': 'application/json'})
    assert response.json()['jobName'] == 'NEW JOB'
    requests.delete(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs/{response.json()['id']}")


def test_get_job_by_id():
    logger.info("Testing job by id")
    response = requests.get(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs/3")
    assert response.json()['id'] == 3

def test_update_job_name():
    logger.info("Testing update job name")
    creation_response = requests.put(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs", data=json.dumps({'jobName': 'UPDATEJOBBEFORE', 'status': 'D', 'createdAt': 1721500200000, 'updatedAt': 1722277800000, 'jobType': 'reporting'})
                            , headers={'Content-Type': 'application/json'})
    jobId = creation_response.json()['id']
    updation_response = requests.post(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs/{jobId}", data=json.dumps({'jobName': 'UPDATEJOBAFTER', 'status': 'D', 'createdAt': 1721500200000, 'updatedAt': 1722277800000, 'jobType': 'reporting'})
                            , headers={'Content-Type': 'application/json'})
    assert updation_response.json()['jobName'] == 'UPDATEJOBAFTER'
    requests.delete(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs/{jobId}")

def test_update_job_status():
    logger.info("Testing update job status")
    creation_response = requests.put(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs", data=json.dumps({'jobName': 'UPDATEJOBBEFORE', 'status': 'D', 'createdAt': 1721500200000, 'updatedAt': 1722277800000, 'jobType': 'reporting'})
                            , headers={'Content-Type': 'application/json'})
    jobId = creation_response.json()['id']
    updation_response = requests.post(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs/{jobId}", data=json.dumps({'jobName': 'UPDATEJOBAFTER', 'status': 'L', 'createdAt': 1721500200000, 'updatedAt': 1722277800000, 'jobType': 'reporting'})
                            , headers={'Content-Type': 'application/json'})
    assert updation_response.json()['status'] == 'L'
    requests.delete(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs/{jobId}")


def test_delete_job():
    logger.info("Testing delete job")
    creation_response = requests.put(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs", data=json.dumps({'jobName': 'UPDATEJOBBEFORE', 'status': 'D', 'createdAt': 1721500200000, 'updatedAt': 1722277800000, 'jobType': 'reporting'})
                            , headers={'Content-Type': 'application/json'})
    jobId = creation_response.json()['id']
    deletion_response = requests.delete(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs/{jobId}")
    assert deletion_response.text == 'Delete Successful'
    requests.delete(f"http://{WEBAPP_HOSTNAME}:8080/api/jobs/{jobId}")